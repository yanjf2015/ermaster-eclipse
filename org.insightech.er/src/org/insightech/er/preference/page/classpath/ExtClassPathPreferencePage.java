package org.insightech.er.preference.page.classpath;

import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.insightech.er.ResourceString;
import org.insightech.er.common.widgets.CompositeFactory;
import org.insightech.er.preference.PreferenceInitializer;

public class ExtClassPathPreferencePage extends PreferencePage implements
		IWorkbenchPreferencePage {

	private DirectoryFieldEditor extDir;

	private Button downloadButton;

	public void init(IWorkbench workbench) {
	}

	@Override
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;

		composite.setLayout(gridLayout);

		this.extDir = new DirectoryFieldEditor("",
				ResourceString.getResourceString("label.ext.classpath"),
				composite);
		/*
		CompositeFactory.filler(composite, 2);
		this.downloadButton = CompositeFactory.createFillButton(composite,
				"Download");
*/
		this.extDir.setFocus();

		this.setData();

		return composite;
	}

	private void setData() {
		String path = PreferenceInitializer.getExtendedClasspath();
		this.extDir.setStringValue(path);
	}

	@Override
	protected void performDefaults() {
		PreferenceInitializer.saveExtendedClasspath(null);

		setData();

		super.performDefaults();
	}

	@Override
	public boolean performOk() {
		PreferenceInitializer.saveExtendedClasspath(this.extDir
				.getStringValue());

		return super.performOk();
	}

}
