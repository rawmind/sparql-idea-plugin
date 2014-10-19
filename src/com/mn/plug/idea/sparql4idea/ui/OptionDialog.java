package com.mn.plug.idea.sparql4idea.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.components.JBPanel;
import com.mn.plug.idea.sparql4idea.core.DbLink;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

/**
 * @author Andrey Kovrov
 */
public class OptionDialog extends DialogWrapper {

  JBPanel panel;
  JButton ok;
  JButton cancel;
  private final JTextField name;
  private final JTextField uri;
  private final MainPanel parent;

  protected OptionDialog(@Nullable Project project, final MainPanel parent) {
    super(project);
    this.parent = parent;
    setTitle("Add Host");
    this.setSize(500, 100);
    panel = new JBPanel();
    ok = new JButton();
    ok.setText("OK");
    cancel = new JButton();
    cancel.setText("Cancel");
    panel.add(ok);
    panel.add(cancel);
    name = new JTextField();
    name.setText("local");
    uri = new JTextField();
    uri.setText("http://127.1:8080/openrdf-sesame/repositories/entitystore");
    panel.add(name);
    panel.add(uri);

    cancel.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // todo: not thread safe (this) !!!!
        close();
      }
    });

    ok.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        parent.dblinkModel.addElement(new DbLink(URI.create(uri.getText()), name.getText()));
        close();
      }
    });
    init();
  }

  @Nullable
  @Override
  protected JComponent createCenterPanel() {
    return panel;
  }

  private void close() {
    this.close(0);
  }

}
