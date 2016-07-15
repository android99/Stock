/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.tom.stock.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import javax.inject.Named;

/** An endpoint class we are exposing */
@Api(
  name = "myApi",
  version = "v1",
  namespace = @ApiNamespace(
    ownerDomain = "backend.stock.tom.com",
    ownerName = "backend.stock.tom.com",
    packagePath=""
  )
)
public class MyEndpoint {

    /** A simple endpoint method that takes a name and says Hi back */
    @ApiMethod(name = "sayHi")
    public MyBean sayHi(@Named("name") String name) {
        MyBean response = new MyBean();
        response.setData("Hi, " + name);

        return response;
    }
    @ApiMethod(name = "login")
    public LoginValidation login(@Named("userid") String userid, @Named("passwd") String passwd){
        LoginValidation result = new LoginValidation();
        result.setUserid(userid);
        if (userid.equals("jack") && passwd.equals("1234")){
            result.setResultCode(1);
            result.setMessage("Success");
        }else{
            result.setResultCode(0);
            result.setMessage("Failed");
        }
        return result;
    }

}
