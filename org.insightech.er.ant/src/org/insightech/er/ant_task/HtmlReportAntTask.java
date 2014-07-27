package org.insightech.er.ant_task;

import java.io.File;
import java.util.List;

import org.eclipse.gef.GraphicalViewer;
import org.eclipse.swt.widgets.Display;
import org.insightech.er.Activator;
import org.insightech.er.editor.model.ERDiagram;
import org.insightech.er.editor.model.dbexport.html.ExportToHtmlManager;
import org.insightech.er.editor.model.dbexport.image.ExportToImageManager;
import org.insightech.er.editor.model.dbexport.image.ImageInfo;
import org.insightech.er.editor.model.dbexport.image.ImageInfoSet;
import org.insightech.er.editor.model.diagram_contents.element.node.category.Category;
import org.insightech.er.editor.view.action.dbexport.ExportToImageAction;
import org.insightech.er.util.io.FileUtils;

public class HtmlReportAntTask extends ERMasterAntTaskBase {

	private String outputDir;

	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doTask(ERDiagram diagram) throws Exception {
		this.outputDir = getAbsolutePath(this.outputDir);
		this.outputDir = this.outputDir + "/dbdocs/";

		this.log("Output to : " + this.outputDir);

		// 出力ディレクトリの削除
		File dir = new File(outputDir);
		FileUtils.deleteDirectory(dir);

		String imageFileName = "image/er.png";
		String outputImageFilePath = this.outputDir + imageFileName;

		this.log("Output image to : " + outputImageFilePath);

		ImageInfo diagramImageInfo = null;
		GraphicalViewer viewer = null;

		Display display = this.getDisplay();

		try {
			viewer = Activator.createGraphicalViewer(display, diagram);

			int format = ExportToImageAction.getFormatType(outputImageFilePath);

			diagramImageInfo = Activator.createImage(display, viewer);
			diagramImageInfo.setFormat(format);
			diagramImageInfo.setPath(imageFileName);

			ExportToImageManager exportToImageManager = new ExportToImageManager(
					diagramImageInfo.getImage(), format, outputImageFilePath);
			exportToImageManager.doProcess();

			ImageInfoSet imageInfoSet = new ImageInfoSet(diagramImageInfo);
			imageInfoSet = this.saveCategoryImages(imageInfoSet, diagram);

			ExportToHtmlManager reportManager = new ExportToHtmlManager(
					this.outputDir, diagram, imageInfoSet);

			this.log("Output html beginning...");

			reportManager.doProcess();

		} finally {
			if (viewer != null) {
				viewer.getContents().deactivate();
			}
			if (diagramImageInfo != null) {
				diagramImageInfo.dispose();
			}
		}
	}

	private ImageInfoSet saveCategoryImages(ImageInfoSet imageInfoSet,
			ERDiagram diagram) throws Exception {
		List<Category> categoryList = diagram.getDiagramContents()
				.getSettings().getCategorySetting().getSelectedCategories();

		for (int i = 0; i < categoryList.size(); i++) {
			Category category = categoryList.get(i);

			String path = "image/category/" + i + ".png";

			ImageInfo imageInfo = null;
			GraphicalViewer viewer = null;

			Display display = this.getDisplay();

			try {
				diagram.setCurrentCategory(category, i + 1);

				viewer = Activator.createGraphicalViewer(display, diagram);

				int format = ExportToImageAction.getFormatType(this.outputDir
						+ path);

				imageInfo = Activator.createImage(display, viewer);

				ExportToImageManager exportToImageManager = new ExportToImageManager(
						imageInfo.getImage(), format, this.outputDir + path);
				exportToImageManager.doProcess();

				imageInfo.setFormat(format);
				imageInfo.setPath(path);

				imageInfoSet.addImageInfo(category, imageInfo);

			} finally {
				if (viewer != null) {
					viewer.getContents().deactivate();
				}
				if (imageInfo != null) {
					imageInfo.dispose();
				}
			}
		}

		return imageInfoSet;
	}

	@Override
	protected void logUsage() {
		this.log("<ermaster.htmlReport> have these attributes. (the attribute with '*' must be set.) ");
		this.log("    * diagramFile - The path of the input .erm file.");
		this.log("      outputDir   - The path of the output directory.");
		this.log("                    The directory named 'dbdocs' is made under this directory.");
		this.log("                    When not specified, the project base directory is used.");
	}
}
