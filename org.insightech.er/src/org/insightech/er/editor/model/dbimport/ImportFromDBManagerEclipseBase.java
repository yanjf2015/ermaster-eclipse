package org.insightech.er.editor.model.dbimport;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

public abstract class ImportFromDBManagerEclipseBase extends
		ImportFromDBManagerBase implements IRunnableWithProgress {

	public class EclipseProgressMonitor implements ProgressMonitor {

		private IProgressMonitor progressMonitor;

		public EclipseProgressMonitor(IProgressMonitor progressMonitor) {
			this.progressMonitor = progressMonitor;
		}

		public void beginTask(String message, int counter) {
			this.progressMonitor.beginTask(message, counter);
		}

		public void worked(int counter) {
			this.progressMonitor.worked(counter);
		}

		public boolean isCanceled() {
			return this.progressMonitor.isCanceled();
		}

		public void done() {
			this.progressMonitor.done();
		}

		public void subTask(String message) {
			this.progressMonitor.subTask(message);
		}

	}

	public class EmptyProgressMonitor implements ProgressMonitor {

		public EmptyProgressMonitor() {
		}

		public void beginTask(String message, int counter) {
		}

		public void worked(int counter) {
		}

		public boolean isCanceled() {
			return false;
		}

		public void done() {
		}

		public void subTask(String message) {
		}

	}

	public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {

		EclipseProgressMonitor eclipseProgressMonitor = new EclipseProgressMonitor(
				monitor);
		this.run(eclipseProgressMonitor);
	}

	public void run() throws InvocationTargetException, InterruptedException {
		this.run(new EmptyProgressMonitor());
	}

}
