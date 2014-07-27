package org.insightech.er.common.widgets;

import java.io.File;
import java.io.IOException;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.insightech.er.Activator;

public class FileText {

	private Text text;

	private Button openBrowseButton;

	private String[] filterExtensions;

	public FileText(Composite parent, int style, String projectPath) {
		this(parent, style, projectPath, new String[0]);
	}

	public FileText(Composite parent, int style, String projectPath,
			String filterExtension) {
		this(parent, style, projectPath, new String[] { filterExtension });
	}

	public FileText(Composite parent, int style, final String projectPath,
			String[] filterExtensions) {
		this.text = new Text(parent, style);

		this.filterExtensions = filterExtensions;

		this.openBrowseButton = new Button(parent, SWT.NONE);
		this.openBrowseButton.setText(JFaceResources.getString("openBrowse"));

		this.openBrowseButton.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				String saveFilePath = Activator.showSaveDialog(text.getText(),
						FileText.this.filterExtensions);
				if (saveFilePath != null) {
					try {
						File saveFile = new File(saveFilePath);
						File projectFile = new File(projectPath);

						saveFilePath = saveFile.getCanonicalPath();
						String canonicalProjectPath = projectFile
								.getCanonicalPath();

						if (saveFilePath.startsWith(canonicalProjectPath)) {
							saveFilePath = saveFilePath
									.substring(canonicalProjectPath.length() + 1);
						}

						text.setText(saveFilePath);

					} catch (IOException ioe) {
						Activator.showExceptionDialog(ioe);
					}
				}
			}
		});
	}

	public void setLayoutData(Object layoutData) {
		this.text.setLayoutData(layoutData);
	}

	public void setText(String text) {
		this.text.setText(text);
		this.text.setSelection(text.length());
	}

	public boolean isBlank() {
		if (this.text.getText().trim().length() == 0) {
			return true;
		}

		return false;
	}

	public String getFilePath() {
		return this.text.getText().trim();
	}

	public void addModifyListener(ModifyListener listener) {
		this.text.addModifyListener(listener);
	}

	public void setFilterExtension(String filterExtension) {
		this.filterExtensions = new String[] { filterExtension };
	}

}
