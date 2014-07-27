package org.insightech.er.editor.view.action.dbexport;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PlatformUI;
import org.insightech.er.Activator;
import org.insightech.er.ImageKey;
import org.insightech.er.ResourceString;
import org.insightech.er.common.exception.InputException;
import org.insightech.er.editor.ERDiagramEditor;
import org.insightech.er.editor.controller.command.settings.ChangeSettingsCommand;
import org.insightech.er.editor.model.ERDiagram;
import org.insightech.er.editor.model.dbexport.html.ExportToHtmlWithProgressManager;
import org.insightech.er.editor.model.dbexport.image.ImageInfo;
import org.insightech.er.editor.model.dbexport.image.ImageInfoSet;
import org.insightech.er.editor.model.diagram_contents.element.node.category.Category;
import org.insightech.er.editor.model.settings.ExportSetting;
import org.insightech.er.editor.model.settings.Settings;
import org.insightech.er.util.Check;
import org.insightech.er.util.io.FileUtils;

public class ExportToHtmlAction extends AbstractExportAction {

	public static final String ID = ExportToHtmlAction.class.getName();

	private static final String OUTPUT_DIR = "/dbdocs/";

	public ExportToHtmlAction(ERDiagramEditor editor) {
		super(ID, ResourceString.getResourceString("action.title.export.html"),
				editor);
		this.setImageDescriptor(Activator
				.getImageDescriptor(ImageKey.EXPORT_TO_HTML));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getSaveFilePath(IEditorPart editorPart,
			GraphicalViewer viewer, ExportSetting exportSetting) {

		IFile file = ((IFileEditorInput) editorPart.getEditorInput()).getFile();

		DirectoryDialog fileDialog = new DirectoryDialog(editorPart
				.getEditorSite().getShell(), SWT.SAVE);

		IProject project = file.getProject();

		String path = exportSetting.getHtmlOutput();
		if (Check.isEmpty(path)) {
			path = project.getLocation().toString();
		}
		fileDialog.setFilterPath(path);
		fileDialog.setMessage(ResourceString
				.getResourceString("dialog.message.export.html.dir.select"));

		String saveFilePath = fileDialog.open();

		if (saveFilePath != null) {
			ERDiagram diagram = this.getDiagram();

			if (!saveFilePath.equals(diagram.getDiagramContents().getSettings()
					.getExportSetting().getHtmlOutput())) {
				Settings newSettings = (Settings) diagram.getDiagramContents()
						.getSettings().clone();
				newSettings.getExportSetting().setHtmlOutput(saveFilePath);

				ChangeSettingsCommand command = new ChangeSettingsCommand(
						diagram, newSettings, false);
				this.execute(command);
			}

			saveFilePath = saveFilePath + OUTPUT_DIR;
		}

		return saveFilePath;
	}

	@Override
	protected String getConfirmOverrideMessage() {
		return "dialog.message.update.html.export.dir";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void save(IEditorPart editorPart, GraphicalViewer viewer,
			String saveDirPath) throws Exception {

		ERDiagram diagram = this.getDiagram();

		Category currentCategory = diagram.getCurrentCategory();
		int currentPageIndex = diagram.getPageIndex();

		try {
			ProgressMonitorDialog monitor = new ProgressMonitorDialog(
					PlatformUI.getWorkbench().getActiveWorkbenchWindow()
							.getShell());

			boolean outputImage = true;

			File dir = new File(saveDirPath);
			FileUtils.deleteDirectory(dir);

			ImageInfoSet imageInfoSet = null;

			if (outputImage) {
				diagram.setCurrentCategory(null, 0);

				ImageInfo diagramImageInfo = ExportToImageAction.outputImage(
						monitor, viewer, saveDirPath, "image/er.png");

				if (diagramImageInfo == null) {
					throw new InputException(null);
				}

				imageInfoSet = new ImageInfoSet(diagramImageInfo);

				List<Category> categoryList = this.getDiagram()
						.getDiagramContents().getSettings()
						.getCategorySetting().getSelectedCategories();

				for (int i = 0; i < categoryList.size(); i++) {
					Category category = categoryList.get(i);

					diagram.setCurrentCategory(category, i + 1);

					String categoryImageFilePath = "image/category/" + i
							+ ".png";

					ImageInfo imageInfo = ExportToImageAction
							.outputImage(monitor, viewer, saveDirPath,
									categoryImageFilePath);

					if (imageInfo == null) {
						throw new InputException(null);
					}

					imageInfoSet.addImageInfo(category, imageInfo);
				}
			}

			ExportToHtmlWithProgressManager manager = new ExportToHtmlWithProgressManager(
					saveDirPath, diagram, imageInfoSet);
			monitor.run(true, true, manager);

			if (manager.getException() != null) {
				throw manager.getException();
			}

		} catch (IOException e) {
			Activator.showMessageDialog(e.getMessage());

		} catch (InterruptedException e) {

		} catch (Exception e) {
			Activator.showExceptionDialog(e);

		} finally {
			diagram.setCurrentCategory(currentCategory, currentPageIndex);
			diagram.refresh();
		}

		this.refreshProject();
	}

	@Override
	protected String[] getFilterExtensions() {
		return null;
	}

	@Override
	protected String getDefaultExtension() {
		return "";
	}

}
