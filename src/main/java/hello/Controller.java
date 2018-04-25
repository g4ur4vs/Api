package hello;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hello.model.CategoryDetails;
import hello.model.EntityGroupMessage;
import hello.model.EntityMessage;
import hello.model.FcmResponse;
import hello.model.GetResources;
import hello.model.LoginParams;
import hello.model.RegisterUser;
import hello.model.Resources;
import hello.model.SearchResults;
import hello.model.SendResponse;
import hello.model.UpdateCategory;
import hello.model.UpdateUsertype;
import hello.model.User;
import hello.model.ValidEmail;
import hello.util.StatVar;

@RestController
public class Controller {

	private DbHelper db = new DbHelper();

	/*
	 * @RequestMapping("/getUserById") public User getAllUser(@RequestParam(value =
	 * "id") int id) throws Exception {
	 * 
	 * JSONObject jobject = db.getUser(id); return new
	 * User(jobject.getString("name"), jobject.getInt("id")); }
	 */

	@RequestMapping(value = "/registerUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public SendResponse registerUser(@RequestBody RegisterUser user) throws Exception {

		System.out.println(user.getName() + " " + user.getEmail() + " " + user.getUserType());
		if (!user.getName().isEmpty() && !user.getEmail().isEmpty()) {

			db.registerUser(user.getName(), user.getEmail(), user.getPass(), user.getMobile(), user.getLocation(),
					user.getCategory(), user.getUserType());

			return new SendResponse("User registered successfully", false);
		}
		return new SendResponse("Registration failed", true);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public SendResponse login(@RequestBody LoginParams loginParams) throws Exception {

		if (!loginParams.getEmail().isEmpty() && !loginParams.getPass().isEmpty()) {

			boolean status = db.login(loginParams.getEmail(), loginParams.getPass(), loginParams.getImei(),
					loginParams.getToken());

			if (status) {
				return new SendResponse("Login successful", false);
			} else {
				return new SendResponse("Password didn't match!", true);
			}

		}

		return new SendResponse("Some error occured", true);

	}

	@RequestMapping(value = "/getResources", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String getResources(@RequestBody GetResources resources) throws Exception {

		return db.getResources(resources.getCategory(), resources.getRes_type()).toString();

	}

	@RequestMapping(value = "/sendNoti", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public SendResponse sendNoti(@RequestBody EntityGroupMessage grpMsg) throws Exception {

		System.out.println(grpMsg.getMsg() + "  " + grpMsg.getTitle());
		FcmClient client = new FcmClient();
		client.setAPIKey(StatVar.FCM_KEY);

		EntityMessage msg = new EntityMessage();

		msg.setRegistrationTokenList(grpMsg.getTokenList());

		/*
		 * for(int i=0;i<grpMsg.getTokenList().size();i++) {
		 * System.out.println(grpMsg.getTokenList().get(i).toString()); }
		 */

		/*
		 * msg.addRegistrationToken(
		 * "esA4kuco0fI:APA91bH137U0sZasKLMJkaxn6Ny-QYL2n8iOaHSzcWiIbsecFpFP-Lde5T7rSeKCXwResmRQ8pQ4FwrYBS4P5CsQ-NlMaizTzeH57bNqnqZcXpFt0zHCVdGzbTF-YrwKavoBMXefuZmW"
		 * );
		 */

		msg.putStringData("title", grpMsg.getTitle());
		msg.putStringData("message", grpMsg.getMsg());

		FcmResponse res = client.pushToEntities(msg);
		/*
		 * JSONObject object=new JSONObject(); JSONObject object1=new JSONObject();
		 * object.put("title", grpMsg.getTitle().toString()); object.put("message",
		 * grpMsg.getMsg().toString());
		 * 
		 * object1.put("data", object);
		 * 
		 * FcmResponse res = client.pushNotify(object1);
		 */

		db.addNotiToDB(grpMsg.getTitle(), grpMsg.getMsg(), grpMsg.getCreatedBy());
		System.out.println(res);
		return new SendResponse(res.toString(), false);

	}

	@RequestMapping(value = "/getAllNoti", method = RequestMethod.GET)
	public String getNotiFromDb() throws Exception {

		JSONArray response = db.getNotification();

		return response.toString();

	}

	@RequestMapping(value = "/checkUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public SendResponse checkuser(@RequestBody User user) throws Exception {

		JSONObject response = db.isEmailValid(user.getEmail());

		if (response != null) {
			response.getBoolean("isRegistered");
			response.getString("email");

			return new SendResponse("" + response.getBoolean("isRegistered"), false);
		} else {
			return new SendResponse("User not found, Contact Admin", true);
		}

	}

	@RequestMapping(value = "/checkConn")
	public void checkConn() throws Exception {

		db.dbconnect();

	}

	@RequestMapping(value = "/insertEmail", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public SendResponse insertEmail(@RequestBody ValidEmail email) throws Exception {

		return db.insert(email.getEmail().toLowerCase());

	}

	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public SendResponse deleteUser(@RequestBody ValidEmail email) throws Exception {

		return db.delete(email.getEmail());

	}

	@RequestMapping(value = "/deleteResource", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public SendResponse deleteResource(@RequestBody Resources name) throws Exception {

		return db.deleteResource(name.getUrl());

	}

	@RequestMapping(value = "/insertResources", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public SendResponse insertresources(@RequestBody Resources res) throws Exception {

		String url, name, description, category, res_type;
		url = res.getUrl();
		name = res.getName();
		description = res.getDescription();
		category = res.getCategory();
		res_type = res.getRes_type();

		return db.insert_resources(name, url, description, category, res_type);

	}

	@RequestMapping(value = "/getToken", method = RequestMethod.GET)
	public String getToken(@RequestParam(value = "category") String category) throws Exception {
		System.out.println("RECIEVED-----" + db.getToken(category).toString());
		return db.getToken(category).toString();

	}

	@RequestMapping(value = "/updateCategory", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public SendResponse updateCategory(@RequestBody UpdateCategory obj) throws Exception {

		boolean status = db.updateCategory(obj.getEmail(), obj.getCategory());

		if (status) {
			return new SendResponse("User updated.", false);
		} else {
			return new SendResponse("Some error occured!", true);
		}

	}

	@RequestMapping(value = "/updateUserType", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public SendResponse updateUsertype(@RequestBody UpdateUsertype user) throws Exception {

		boolean status = db.updateUsertype(user.getEmail(), user.getUsertype(), user.getCategory());

		if (status) {
			return new SendResponse("User updated.", false);
		} else {
			return new SendResponse("Some error occured!", true);
		}

	}

	@RequestMapping(value = "/categoryDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String categoryDetails(@RequestBody CategoryDetails details) throws Exception {

		JSONArray response = db.categoryDetails(details.getCategory(), details.getUsertype());

		return response.toString();

	}

	@RequestMapping(value = "/searchUser")
	public String searchUser(@RequestParam(value = "char") String character) throws Exception {

		ArrayList<SearchResults> userList = db.searchContacts(character);

		JSONArray jsonArray = new JSONArray();
		if (userList != null && userList.size() > 0) {

			for (int i = 0; i < userList.size(); i++) {

				SearchResults obj = userList.get(i);
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("name", obj.getName());
				jsonObject.put("category", obj.getCategory());
				jsonObject.put("email", obj.getEmail());
				jsonObject.put("location", obj.getLocation());
				jsonObject.put("mobile", obj.getMobile());
				jsonObject.put("user_type", obj.getUserType());

				jsonArray.put(jsonObject);
			}
		} else {

		}

		return jsonArray.toString();

	}

	@RequestMapping(value = "/getAllUsers")
	public String getAllUsers() throws Exception {

		return db.getAllUsers().toString();
	}

	@RequestMapping(value = "/getUserDetail")
	public RegisterUser getUserDetail(@RequestParam(value = "email") String email) throws Exception {

		return db.getUser(email);
	}
}
