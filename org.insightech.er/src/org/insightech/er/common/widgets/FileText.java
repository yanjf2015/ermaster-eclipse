package org.insightech.er.common.widgets;

import java.io.File;

import org.eclipse.swt.widgets.Composite;
import org.insightech.er.ERDiagramActivator;

public class FileText extends AbstractPathText {

	private String[] filterExtensions;

	private String defaultFileName;

	public FileText(Composite parent, File projectDir, String defaultFileName,
			String filterExtension) {
		this(parent, projectDir, defaultFileName, filterExtension, true);
	}

	public FileText(Composite parent, File projectDir, String defaultFileName,
			String filterExtension, boolean indent) {
		this(parent, projectDir, defaultFileName,
				new String[] { filterExtension }, indent);
	}

	public FileText(Composite parent, final File projectDir,
			final String defaultFileName, String[] filterExtensions) {
		this(parent, projectDir, defaultFileName, filterExtensions, true);
	}

	public FileText(Composite parent, final File projectDir,
			final String defaultFileName, String[] filterExtensions,
			boolean indent) {
		super(parent, projectDir, indent);

		this.filterExtensions = filterExtensions;
		this.defaultFileName = defaultFileName;
	}

	public void setFilterExtension(String filterExtension) {
		this.filterExtensions = new String[] { filterExtension };
	}

	@Override
	protected String selectPathByDilaog() {
		return ERDiagramActivator.showSaveDialog(this.projectDir, this.defaultFileName,
				this.getFilePath(), this.filterExtensions);
	}

}
