package edu.xmu.nmr.custom.extensions.beans;

import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.part.EditorPart;

public class EditorPageBean {

	private String id;
	private String name;
	private IEditorInput editorInput;
	private EditorPart editorPart;

	public EditorPageBean() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public IEditorInput getEditorInput() {
		return editorInput;
	}

	public void setEditorInput(IEditorInput editorInput) {
		this.editorInput = editorInput;
	}

	public EditorPart getEditorPart() {
		return editorPart;
	}

	public void setEditorPart(EditorPart editorPart) {
		this.editorPart = editorPart;
	}

}
