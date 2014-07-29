package org.insightech.er.editor.view.dialog.dbexport;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.insightech.er.common.dialog.AbstractDialog;

public abstract class AbstractExportDialog extends AbstractDialog {

	protected IEditorPart editorPart;

	public AbstractExportDialog(Shell parentShell, int numColumns,
			IEditorPart editorPart) {
		super(parentShell, numColumns);

		this.editorPart = editorPart;
	}

	protected String getProjectPath() {
		IFile file = ((IFileEditorInput) this.editorPart.getEditorInput())
				.getFile();

		IProject project = file.getProject();

		return project.getLocation().toString();
	}

	protected String getOutputFilePath(String extention) {
		IFile file = ((IFileEditorInput) editorPart.getEditorInput()).getFile();
		String outputImage = file.getLocation().toOSString();
		outputImage = outputImage.substring(0, outputImage.lastIndexOf("."))
				+ extention;

		return outputImage;
	}

	protected String getOutputFileName(String extention) {
		File file = new File(this.getOutputFilePath(extention));

		return file.getName();
	}

}
