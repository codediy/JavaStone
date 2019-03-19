import java.util.List;

/*Primary表达式
* primary : "( expr ")" | NUMBER | IDENTIFIER | STRING
* */
public class PrimaryExpr extends ASTList{

    public PrimaryExpr(List<ASTree> list) {
        super(list);
    }

    /*PrimaryExpr
    * size() = 1 是 NUMBER|IDENTIFIER|STRING
    * size() > 1 则 "(" expr ")"
    * */
    public static ASTree create(List<ASTree> c){
        return c.size() == 1 ? c.get(0) : new PrimaryExpr(c);
    }
}
