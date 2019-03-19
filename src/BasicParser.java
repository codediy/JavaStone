import java.util.HashSet;

public class BasicParser {
    /*保留字*/
    HashSet<String> reserved = new HashSet<>();
    /*运算符*/
    Parser.Operators operators = new Parser.Operators();
    /*空的规则*/
    Parser expr0 = Parser.rule();

    /*Primary*/
    Parser primary = Parser.rule(PrimaryExpr.class)
            .or(Parser.rule().sep("(").ast(expr0).sep(")"),         /*"(" expr ")"*/
                    Parser.rule().number(NumberLiteral.class),      /*| NUMBER*/
                    Parser.rule().identifier(Name.class, reserved), /*| IDENTIFIER*/
                    Parser.rule().string(StringLiteral.class)       /*| STRING*/
            );
    /*factor*/
    Parser factor = Parser.rule()
            .or(
                    Parser.rule(NegativeExpr.class).sep("-").ast(primary),  /*- primary*/
                    primary                                                 /*| primary*/
            );
    /*BinaryExpr */
    Parser expr = expr0.expression(BinaryExpr.class, factor, operators);
    Parser statement0 = Parser.rule();
    /*Block*/
    Parser block = Parser.rule(BlockStmnt.class)
            .sep("{")                                                       /*{*/
            .option(statement0)
            .repeat(Parser.rule().sep(";", Token.EOL).option(statement0))    /*statement[; EOL  statement]* */
            .sep("}");                                                      /*}*/
    /*Simple*/
    Parser simple = Parser.rule(PrimaryExpr.class).ast(expr);
    /*Statment*/
    Parser statement = statement0
            .or(
                    Parser.rule(IfStmnt.class).sep("if").ast(expr).ast(block)      /*If*/
                            .option(Parser.rule().sep("else").ast(block)),
                    Parser.rule(WhilteStmnt.class).sep("while").ast(expr).ast(block), /*While*/
                    simple
            );
    /*Program*/
    Parser program = Parser.rule()
            .or(
                statement,
                Parser.rule(NullStmnt.class).sep(";",Token.EOL)
            );

    public  BasicParser(){
        /*注册保留符号*/
        reserved.add(";");
        reserved.add("}");
        reserved.add(Token.EOL);

        /*运算符 优先级 结合性*/
        operators.add("=",1, Parser.Operators.RIGHT);
        operators.add("==",2, Parser.Operators.LEFT);
        operators.add(">",2, Parser.Operators.LEFT);
        operators.add("<",2, Parser.Operators.LEFT);
        operators.add("+",3, Parser.Operators.LEFT);
        operators.add("-",3, Parser.Operators.LEFT);
        operators.add("*",4, Parser.Operators.LEFT);
        operators.add("/",4, Parser.Operators.LEFT);
        operators.add("%",4, Parser.Operators.LEFT);
    }

    /*解析入口*/
    public ASTree parse(Lexer lexer) throws ParseException{
        return program.parse(lexer);
    }
}
