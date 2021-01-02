import { Component, OnInit } from '@angular/core';
import {AuthentificationService} from '../authentification.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private authenficiation:AuthentificationService,private router:Router) { }

  ngOnInit(): void {
  }

  onLogin(value) {
    console.log(value);
    this.authenficiation.login(value).subscribe(reponse=>{
      let jwt=reponse.headers.get('Authorization');
      this.authenficiation.saveToken(jwt);
      this.router.navigateByUrl("/");

    },err=>{
      console.log(err);
    })

  }
  isAdmin(){
    return this.authenficiation.isAdmin();
  }
  isUser(){
    return this.authenficiation.isUser();
  }
}
