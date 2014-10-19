package com.mn.plug.idea.sparql4idea.editor;

import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorProvider;
import com.intellij.openapi.fileEditor.impl.text.TextEditorProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.mn.plug.idea.sparql4idea.TextEditorWrapper;
import org.jetbrains.annotations.NotNull;

/**
 * @author Andrey Kovrov
 */
public class SparqlFileEditorProvider extends TextEditorProvider implements FileEditorProvider {


  @NotNull
  @Override
  public FileEditor createEditor(@NotNull Project project, @NotNull VirtualFile file) {
    return new TextEditorWrapper(project, file, this);
  }

  @NotNull
  @Override
  public String getEditorTypeId() {
    return "sparql-editor";
  }
}
