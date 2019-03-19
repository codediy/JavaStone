public class ParserRunner {

    public static void main(String[] args) throws ParseException{
        Lexer lexer = new Lexer(new CodeDialog());
        BasicParser bp = new BasicParser();
        while(lexer.peek(0) != Token.EOF){
            ASTree ast = bp.parse(lexer);
            System.out.println("=>"+ ast.toString());
        }
    }
}
