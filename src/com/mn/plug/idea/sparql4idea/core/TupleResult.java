package com.mn.plug.idea.sparql4idea.core;

import com.mn.plug.idea.sparql4idea.lang.NameSpaces;
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
  private final NameSpaces normalizer = new NameSpaces();

  public TupleResult(TupleQueryResult tupleQueryResult, long queryTime, long connectionTime) {
    super(queryTime, connectionTime);
    DefaultTableModel tableModel = new DefaultTableModel(){
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };
    try {
      for (String binding : tupleQueryResult.getBindingNames()) {
        tableModel.addColumn(binding);
      }
      while (tupleQueryResult.hasNext()) {
        Vector<String> vector = new Vector<String>();
        BindingSet row = tupleQueryResult.next();
        for (String binding : tupleQueryResult.getBindingNames()) {
          String e = row.getValue(binding).stringValue();
          vector.add(normalizer.normalize(e));
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
