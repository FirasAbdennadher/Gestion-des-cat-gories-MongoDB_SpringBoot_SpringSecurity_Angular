import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {AuthentificationService} from './authentification.service';

@Injectable({
  providedIn: 'root'
})
export class CatalogueService {
  public host:string="http://localhost:8087";

  constructor(private http: HttpClient, private authentiticationService:AuthentificationService) {}
    getRessources(url){
      return this.http.get(url);
    }

    DeleteRessources(url){
    let header=new HttpHeaders({'Authorization':this.authentiticationService.jwt});
      return this.http.delete(url,{headers:header});
    }

  postRessources(url,data){
    let headers=new HttpHeaders({'Authorization':this.authentiticationService.jwt});
    return this.http.post(url,data,{headers:headers});
  }



  getAllCatrogries(){
      return this.http.get(this.host+"/categories");
    }

  putRessources(url,data){
    let headers=new HttpHeaders({'Authorization':this.authentiticationService.jwt});
    return this.http.put(url,data,{headers:headers});
  }



}
