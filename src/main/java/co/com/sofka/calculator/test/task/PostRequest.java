package co.com.sofka.calculator.test.task;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.Post;

import java.util.HashMap;

public class PostRequest implements Task {



    private String path;
    private Object body;
    private HashMap<String, Object> headers;

    public PostRequest(HashMap<String, Object> headers, String path, Object body){
        this.headers = headers;
        this.path = path;
        this.body = body;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Post.to(path)
                .with(request -> request.headers(headers).body(body))
        );
    }

    public static PostRequest execute(HashMap<String, Object> headers, String path, Object body){
        return Tasks.instrumented(PostRequest.class, headers, path, body);
    }
}
