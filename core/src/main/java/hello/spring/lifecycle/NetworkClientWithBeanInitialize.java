package hello.spring.lifecycle;

public class NetworkClientWithBeanInitialize {
    private String url;

    public NetworkClientWithBeanInitialize() {
        System.out.println("생성자 호출 url = " + url);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void connect() {
        System.out.println("connect: " + url);
    }

    public void call(String message) {
        System.out.println("call : " + url + " message = " + message);
    }

    public void disconnect() {
        System.out.println("close : " + url);
    }

    public void init(){
        System.out.println("NetworkClient.afterPropertiesSet");
        connect();
        call("초기화 연결 메시지");
    }

    public void close() {
        System.out.println("NetworkClient.destroy");
        disconnect();
    }
}
