import { Component, OnInit } from '@angular/core';
import {CatalogueService} from '../catalogue.service';
import {ActivatedRoute, NavigationEnd, Router} from '@angular/router';

@Component({
  selector: 'app-produits',
  templateUrl: './produits.component.html',
  styleUrls: ['./produits.component.css']
})
export class ProduitsComponent implements OnInit {
products;
  constructor(private catService:CatalogueService,private route:ActivatedRoute,private router:Router) {
    this.router.events.subscribe(event=>{ // event trigger ll changement du catégories
      if(event instanceof NavigationEnd){
        console.log(this.route);
        let url=atob(this.route.snapshot.params.urlProd);
        this.getProducts(url);

      }
    })
  }

  ngOnInit(): void {

  }

  getProducts(url){
  this.catService.getRessources(url)
    .subscribe(data=>{
      this.products=data;
    },err=>{
      console.log(err);
    })
  }

}
