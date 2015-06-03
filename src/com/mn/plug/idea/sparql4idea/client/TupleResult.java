package com.mn.plug.idea.sparql4idea.client;

import com.mn.plug.idea.sparql4idea.lang.NameSpaces;
import com.mn.plug.idea.sparql4idea.ui.ResultTableModel;
import org.openrdf.model.Value;
import org.openrdf.query.BindingSet;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.TupleQueryResult;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

/**
 * @author Andrey Kovrov
 */
public class TupleResult extends AbstractResult {

  private final TableModel model;
  private static final String EMPTY_STRING = "";

  public TupleResult(TupleQueryResult tupleQueryResult, long queryTime, long connectionTime) {
    super(queryTime, connectionTime);
    if (tupleQueryResult == null) {
      model = EMPTY_MODEL;
      return;
    }
    DefaultTableModel tableModel = new ResultTableModel();
    try {
      List<String> bindingNames = tupleQueryResult.getBindingNames();
      if (bindingNames == null) {
        //todo: for ASK query
        bindingNames = Collections.emptyList();
      }
      for (String binding : bindingNames) {
        tableModel.addColumn(binding);
      }
      while (tupleQueryResult.hasNext()) {
        Vector<String> vector = new Vector<String>();
        BindingSet row = tupleQueryResult.next();
        for (String binding : bindingNames) {
          Value value = row.getValue(binding);
          String e = value == null ? EMPTY_STRING : value.stringValue();
          vector.add(NameSpaces.normalize(e));
        }
        tableModel.addRow(vector);
      }
    } catch (QueryEvaluationException e) {
      setErrorMessage(e.getMessage());
    } finally {
      try {
        tupleQueryResult.close();
      } catch (QueryEvaluationException e) {
        // skip
      }
    }
    model = tableModel;
  }

  public TupleResult(String error) {
    super(error);
    model = EMPTY_MODEL;
  }

  @Override
  public TableModel getTableDataModel() {
    return model;
  }

}