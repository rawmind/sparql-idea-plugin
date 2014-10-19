package com.mn.plug.idea.sparql4idea.vfs;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileSystem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Andrey Kovrov
 */
public class SparqlVirtualFile extends VirtualFile {

  @NotNull
  @Override
  public String getName() {
    return "";
  }

  @NotNull
  @Override
  public VirtualFileSystem getFileSystem() {
    return null;
  }

  @NotNull
  @Override
  public String getPath() {
    return null;
  }

  @Override
  public boolean isWritable() {
    return false;
  }

  @Override
  public boolean isDirectory() {
    return false;
  }

  @Override
  public boolean isValid() {
    return false;
  }

  @Override
  public VirtualFile getParent() {
    return null;
  }

  @Override
  public VirtualFile[] getChildren() {
    return new VirtualFile[0];
  }

  @NotNull
  @Override
  public OutputStream getOutputStream(Object requestor, long newModificationStamp, long newTimeStamp) throws IOException {
    return null;
  }

  @NotNull
  @Override
  public byte[] contentsToByteArray() throws IOException {
    return new byte[0];
  }

  @Override
  public long getTimeStamp() {
    return 0;
  }

  @Override
  public long getLength() {
    return 0;
  }

  @Override
  public void refresh(boolean asynchronous, boolean recursive, @Nullable Runnable postRunnable) {

  }

  @Override
  public InputStream getInputStream() throws IOException {
    return null;
  }
}
