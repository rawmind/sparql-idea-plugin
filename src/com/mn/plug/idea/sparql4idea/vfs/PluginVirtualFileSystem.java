package com.mn.plug.idea.sparql4idea.vfs;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.ex.dummy.DummyCachingFileSystem;
import org.jetbrains.annotations.NotNull;

/**
 * @author Andrey Kovrov
 */
public class PluginVirtualFileSystem extends DummyCachingFileSystem<VirtualFile> {

  public PluginVirtualFileSystem(String protocol) {
    super(protocol);
  }

  @Override
  protected VirtualFile findFileByPathInner(@NotNull String s) {
    return new SparqlVirtualFile();
  }
}
