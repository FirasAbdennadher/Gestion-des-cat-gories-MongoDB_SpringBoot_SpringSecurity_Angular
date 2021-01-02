import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {JwtHelperService} from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class AuthentificationService {
  host2:string="http://localhost:8089";
  jwt:string;
  userName:string;
  roles:Array<string>;
  constructor(private http:HttpClient) { }

  login(data){
    console.log(data);
  return this.http.post(this.host2+"/login",data,{observe:'response'});
  }

  saveToken(jwt: string) {
    localStorage.setItem("token",jwt);
    this.jwt=jwt;
   this.parseJWT();
  }

   parseJWT() {
    let jwt_Helper =  new JwtHelperService();
    let objJwt=jwt_Helper.decodeToken(this.jwt)
    this.userName=objJwt.obj;
    this.roles=objJwt.roles;
  }

  isAdmin(){
    return this.roles.indexOf("ADMIN")>=0;
  }
  isUser(){
    return this.roles.indexOf("USER")>=0;
  }

  isAutheniticated(){
    return this.roles;
  }


  loadToken() {
    this.jwt=localStorage.getItem("token");
    this.parseJWT();
  }

  logOut() {
    localStorage.removeItem("token");
    this.initParams();
  }
  initParams(){
    this.userName=undefined;
    this.roles=undefined;
    this.jwt=undefined;
  }
}
