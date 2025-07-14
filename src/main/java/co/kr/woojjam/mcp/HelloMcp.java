package co.kr.woojjam.mcp;

/**
 * Hello MCP 문자열을 출력하는 간단한 클래스
 */
public class HelloMcp {
    
    /**
     * "Hello MCP" 문자열을 콘솔에 출력합니다.
     */
    public void printHello() {
        System.out.println("Hello MCP");
    }
    
    /**
     * "Hello MCP" 문자열을 반환합니다.
     * @return Hello MCP 문자열
     */
    public String getHelloMessage() {
        return "Hello MCP";
    }
    
    /**
     * Claude가 작성한 메소드 - "Hello I'm Claude"를 출력합니다.
     */
    public void writeByClaude() {
        System.out.println("Hello I'm Claude");
    }
    
    /**
     * 테스트용 메인 메서드
     */
    public static void main(String[] args) {
        HelloMcp hello = new HelloMcp();
        hello.printHello();
        
        String message = hello.getHelloMessage();
        System.out.println("메시지: " + message);
        
        hello.writeByClaude();
    }
}
