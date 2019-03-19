import java.util.List;

/*
* While语句
* condition
* body
* */
public class WhilteStmnt extends ASTList {
    public WhilteStmnt(List<ASTree> list) {
        super(list);
    }

    public ASTree condition(){
        return child(0);
    }
    public ASTree body(){
        return child(1);
    }

    @Override
    public String toString() {
        return "(while " + condition() + "" + body();
    }
}
