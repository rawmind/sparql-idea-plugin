package com.mn.plug.idea.sparql4idea.vfs;

import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.vfs.NonPhysicalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.ex.temp.TempFileSystem;
import com.intellij.openapi.vfs.newvfs.NewVirtualFile;
import com.intellij.openapi.vfs.newvfs.NewVirtualFileSystem;
import com.mn.plug.idea.sparql4idea.SparqlFileType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Andrey Kovrov
 */
public class SparqlConsole extends VirtualFile implements NonPhysicalFileSystem {

  public static final FakeFileSystem FAKE_FILE_SYSTEM = new FakeFileSystem();

  @NotNull
  @Override
  public String getName() {
    return "Sparql Console";
  }

  @Override
  @NotNull
  public NewVirtualFileSystem getFileSystem() {
    return FAKE_FILE_SYSTEM;
  }

  @NotNull
  @Override
  public String getPath() {
    return "/tmp/console.1";
  }

  @Override
  public boolean isWritable() {
    return true;
  }

  @Override
  public boolean isDirectory() {
    return false;
  }

  @Override
  public boolean isValid() {
    return true;
  }

  @Override
  public VirtualFile getParent() {
    return null;
  }

  @Override
  public VirtualFile[] getChildren() {
    return EMPTY_ARRAY;
  }

  @Nullable
  @Override
  public NewVirtualFile findChild(@NotNull @NonNls String name) {
    return null;
  }

  @NotNull
  @Override
  public OutputStream getOutputStream(Object requestor, long newModificationStamp, long newTimeStamp) throws IOException {
    return new ByteArrayOutputStream();
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

  @NotNull
  @Override
  public FileType getFileType() {
    return SparqlFileType.SPARQL_FILE_TYPE;
  }

  @Override
  public String toString() {
    return "Sparql console emulator";
  }

  @Override
  public long getModificationStamp() {
    return 1l;
  }

  @Nullable
  @Override
  public String getExtension() {
    return "sparql";
  }

  private static class FakeFileSystem extends TempFileSystem implements NonPhysicalFileSystem{}

}
