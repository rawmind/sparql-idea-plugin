package com.mn.plug.idea.sparql4idea;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.TextEditor;
import com.intellij.openapi.fileEditor.impl.text.PsiAwareTextEditorImpl;
import com.intellij.openapi.fileEditor.impl.text.TextEditorProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

/**
 * @author Andrey Kovrov
 */
public class TextEditorWrapper extends PsiAwareTextEditorImpl  implements TextEditor{

  private final VirtualFile virtualFile;

  public TextEditorWrapper(@NotNull Project project, @NotNull VirtualFile file, TextEditorProvider provider) {
    super(project, file, provider);
    this.virtualFile = file;
  }

  @NotNull
  @Override
  public Editor getEditor() {
    return super.getEditor();
  }


}
