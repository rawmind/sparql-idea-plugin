package com.mn.plug.idea.sparql4idea.client;

import com.mn.plug.idea.sparql4idea.lang.NameSpaces;
import com.mn.plug.idea.sparql4idea.ui.ResultTableModel;
import org.openrdf.query.BindingSet;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.TupleQueryResult;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.Vector;

/**
 * @author Andrey Kovrov
 */
public class TupleResult extends AbstractResult {

  private final TableModel model;

  public TupleResult(TupleQueryResult tupleQueryResult, long queryTime, long connectionTime) {
    super(queryTime, connectionTime);
    DefaultTableModel tableModel = new ResultTableModel();
    try {
      for (String binding : tupleQueryResult.getBindingNames()) {
        tableModel.addColumn(binding);
      }
      while (tupleQueryResult.hasNext()) {
        Vector<String> vector = new Vector<String>();
        BindingSet row = tupleQueryResult.next();
        for (String binding : tupleQueryResult.getBindingNames()) {
          String e = row.getValue(binding).stringValue();
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
