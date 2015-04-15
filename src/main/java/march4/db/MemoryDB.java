package march4.db;

import java.util.HashMap;
import java.util.Map;

import march4.model.Project;
import march4.model.User;

public class MemoryDB {
	/*
	 * 흥미로운 방식으로 DB를 구현하셨네요 :)
	 */
	private static Map<Integer,Project> projects = new HashMap<Integer, Project>();
	private static Map<Integer,User> users = new HashMap<Integer, User>();
	
	/*userAPI*/
	public static User getUserById(String key) {
		return users.get(key);
	}

	public static User putUser(User user) {
		/*
		 * 이미 로거 적용하셨던데.. 혹시 여력이 된다면 바로 로거를 사용해보세요 
		 * System.out.println보다 훨씬 장점이 많아요..
		 * 
		 * 참고자료:
		 * 1. [로거의 장점] http://kimeunseokit.tumblr.com/post/76965886665/java-log-system-out-print
		 * 2. [한글 설명] https://sonegy.wordpress.com/category/logback/
		 * 3. [logback doc] http://logback.qos.ch/documentation.html
		 * 4. [slipp.net github] https://github.com/javajigi/slipp/blob/master/src/main/resources-development/logback.xml
		 */
		System.out.println(user);
		for (User usera : users.values()) {
			System.out.println(usera);
		}
		return users.put(user.getUid(), user);
	}
	
	public static Map<Integer, User> getAllUsers() {
		for (User user : users.values()) {
			System.out.println(user);
		}
		return users;
	}
	
	/*projectAPI*/
	public static Project getProjectById(int key) {
		return projects.get(key);
	}
	
	public static Project putProject(Project project) {
		return projects.put(project.getProjectId(), project);
	}
	
	public static Map<Integer, Project> getAllProjectByOwner(int ownerId) {
		Map<Integer,Project> result = new HashMap<Integer,Project>();
		for (Project project : projects.values()) {
			if(project.getOwnerId() == ownerId)
				result.put(project.getProjectId(), project);
		}
		return result;
	}
	
	public static Map<Integer, Project> getAllProjects() {
		return projects;
	}
}
