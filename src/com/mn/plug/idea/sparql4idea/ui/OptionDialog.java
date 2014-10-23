package com.mn.plug.idea.sparql4idea.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import com.mn.plug.idea.sparql4idea.client.DbLink;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.net.URI;

/**
 * @author Andrey Kovrov
 */
public class OptionDialog extends DialogWrapper {

  public static final double LABEL_WEIGHT = 0.1;
  public static final double PANEL_WEIGHT = 1.9;
  public static final Dimension PANEL_MIN_SIZE = new Dimension(400, 50);
  private final JBPanel panel;
  private final JTextField name;
  private final JTextField uri;
  private final MainPanel parent;
  private final ActionType actionType;
  private final JBPopupFactory popupFactory;


  public OptionDialog(@Nullable Project project, final MainPanel parent, ActionType actionType) {
    super(project);
    this.parent = parent;
    this.actionType = actionType;
    setTitle(actionType.getTitle());
    panel = new JBPanel(new GridBagLayout());
    name = new JTextField();
    uri = new JTextField();
    if (actionType == ActionType.MODIFY || actionType == ActionType.DELETE) {
      DbLink selectedItem = (DbLink) parent.dBlinkModel.getSelectedItem();
      name.setText(selectedItem.name);
      uri.setText(selectedItem.uri.toString());
    }
    popupFactory = JBPopupFactory.getInstance();
    panel.setMinimumSize(PANEL_MIN_SIZE);
    name.setEnabled(actionType.modifyAccepted);
    uri.setEnabled(actionType.modifyAccepted);
    setUpLayout();
    init();
  }

  private void setUpLayout() {
    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.BOTH;
    c.anchor = GridBagConstraints.EAST;
    c.gridwidth = 1;
    c.gridheight = 1;
    c.gridx = 0;
    c.gridy = 0;
    c.weightx = LABEL_WEIGHT;
    JBLabel nameLabel = new JBLabel("Name:");
    panel.add(nameLabel, c);
    c.weightx = 1.7;
    c.gridy = 0;
    c.gridx = 1;
    panel.add(name, c);
    c.weightx = LABEL_WEIGHT;
    c.gridy = 1;
    c.gridx = 0;
    JBLabel uriLabel = new JBLabel("URI:");
    panel.add(uriLabel, c);
    c.weightx = PANEL_WEIGHT;
    c.gridy = 1;
    c.gridx = 1;
    panel.add(uri, c);
  }

  @Nullable
  @Override
  protected JComponent createCenterPanel() {
    return panel;
  }

  @Override
  protected void doOKAction() {
    URI inputUri;
    try {
      inputUri = URI.create(uri.getText());
    } catch (IllegalArgumentException e) {
      popupFactory.createMessage("Wrong uri: "+e.getMessage());
      //e.printStackTrace();
      return;
    }
    DbLink anObject = new DbLink(inputUri, name.getText());
    switch (actionType) {
      case ADD:
        parent.dBlinkModel.addElement(anObject);
        parent.dBlinkModel.setSelectedItem(anObject);
        break;
      case MODIFY:
        parent.dBlinkModel.removeElement(parent.dBlinkModel.getSelectedItem());
        parent.dBlinkModel.addElement(anObject);
        parent.dBlinkModel.setSelectedItem(anObject);
        break;
      case DELETE:
        parent.dBlinkModel.removeElement(parent.dBlinkModel.getSelectedItem());
        break;
    }
    super.doOKAction();
  }

  public enum ActionType {
    MODIFY("Edit host", true), DELETE("Remove host", false), ADD("Add new host", true);

    String title;
    boolean modifyAccepted;

    ActionType(String title, boolean modifyAccepted) {
      this.title = title;
      this.modifyAccepted = modifyAccepted;
    }

    public String getTitle() {
      return title;
    }
  }

}
