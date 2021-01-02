import {Component, OnInit} from '@angular/core';
import {AuthentificationService} from './authentification.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'CatMongo';
constructor(private authenficiation:AuthentificationService,private route:Router) {

}

  ngOnInit(): void {
  this.authenficiation.loadToken();
  }

  isAdmin(){
    return this.authenficiation.isAdmin();
  }
  isUser(){
    return this.authenficiation.isUser();
  }
isAuthenticated(){
  return this.authenficiation.isAutheniticated();
}


  logOut() {
    this.authenficiation.logOut();
    //this.route.navigateByUrl("/login");

  }
}
