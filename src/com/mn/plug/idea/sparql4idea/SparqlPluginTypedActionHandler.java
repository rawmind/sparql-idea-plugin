/*
 * IdeaVim - Vim emulator for IDEs based on the IntelliJ platform
 * Copyright (C) 2003-2014 The IdeaVim authors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.mn.plug.idea.sparql4idea;

import com.intellij.codeInsight.generation.CommentByLineCommentHandler;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.TypedActionHandler;
import com.intellij.openapi.vfs.ex.temp.TempFileSystem;
import com.intellij.psi.PsiManager;
import com.mn.plug.idea.sparql4idea.vfs.SparqlConsole;
import org.jetbrains.annotations.NotNull;

public class SparqlPluginTypedActionHandler implements TypedActionHandler {

  private static final Logger logger = Logger.getInstance(SparqlPluginTypedActionHandler.class.getName());

  private final TypedActionHandler origHandler;

  public SparqlPluginTypedActionHandler(TypedActionHandler origHandler) {
    this.origHandler = origHandler;

  }

  @Override
  public void execute(@NotNull final Editor editor, final char charTyped, @NotNull final DataContext context) {
    CommentByLineCommentHandler commentByLineCommentHandler = new CommentByLineCommentHandler();
    PsiManager psiManager = PsiManager.getInstance(editor.getProject());
    TempFileSystem component = ApplicationManager.getApplication().getComponent(TempFileSystem.class);
    CommonDataKeys.VIRTUAL_FILE.getData(context);
    //VirtualFile root = component.getRoot();
    SparqlConsole sparqlConsole = new SparqlConsole();
//

    origHandler.execute(editor, charTyped, context);
  }

}
