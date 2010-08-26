package org.insightech.er.editor.model.diagram_contents.element.node.note;

import java.util.List;

import org.insightech.er.editor.model.diagram_contents.element.connection.ConnectionElement;
import org.insightech.er.editor.model.diagram_contents.element.node.NodeElement;
import org.insightech.er.util.Format;

/**
 * �m�[�g�̃��f��
 * 
 * @author nakajima
 * 
 */
public class Note extends NodeElement implements Comparable<Note> {

	private static final long serialVersionUID = -8810455349879962852L;

	public static final String PROPERTY_CHANGE_NOTE = "note";

	private String text;

	/**
	 * �m�[�g�̖{����ԋp���܂��B
	 * 
	 * @return
	 */
	public String getText() {
		return text;
	}

	/**
	 * �m�[�g�̖{����ݒ肵�܂��B
	 * 
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;

		this.firePropertyChange(PROPERTY_CHANGE_NOTE, null, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<NodeElement> getReferredElementList() {
		List<NodeElement> referredElementList = super.getReferredElementList();

		for (ConnectionElement connectionElement : this.getIncomings()) {
			NodeElement sourceElement = connectionElement.getSource();
			referredElementList.add(sourceElement);
		}

		return referredElementList;
	}

	public String getDescription() {
		return "";
	}

	public int compareTo(Note other) {
		int compareTo = 0;

		compareTo = Format.null2blank(this.text).compareTo(
				Format.null2blank(other.text));

		return compareTo;
	}

	public String getName() {
		String name = text;
		if (name == null) {
			name = "";

		} else if (name.length() > 20) {
			name = name.substring(0, 20);
		}

		return name;
	}

	public String getObjectType() {
		return "note";
	}
}