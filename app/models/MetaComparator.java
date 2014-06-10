package models;

import java.util.Comparator;

public class MetaComparator implements Comparator<Meta> {
	@Override
    public int compare(Meta m1, Meta m2) {
       return m1.getPrioridade().compareTo(m2.getPrioridade());
    }
}