package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.LinkedList;

public class PlayerHistory {

    private final LinkedList<Integer> history = new LinkedList<>();

    public void record(int pos) {
        history.add(pos);
        if (history.size() > 20) {
            history.removeFirst();
        }
    }

    public int last() {
        return history.size() >= 1 ? history.get(history.size() - 1) : 1;
    }

    public int beforeLast() {
        return history.size() >= 2 ? history.get(history.size() - 2) : last();
    }

    public int twoBefore() {
        return history.size() >= 3 ? history.get(history.size() - 3) : beforeLast();
    }
}
