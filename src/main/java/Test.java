import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingExclude;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;

@Service
@RemotingDestination(channels={"my-amf","my-secure-amf"})
public class Test {

//	@RemotingInclude
//	public Product read(String id) {
//		...
//	}
//	
//	@RemotingExclude
//	public Product create(Product product){
//		...
//	}
//	
//	@RemotingInclude
//	public Product update(Product product){
//		...
//	}
//	
//	@RemotingExclude
//	public void delete(Product product) {
//		...
//	}
	
	@RemotingInclude
	public void test(){
		System.out.println("hello world");
	}
}