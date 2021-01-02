import { Component, OnInit } from '@angular/core';
import {CatalogueService} from '../catalogue.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.css']
})
export class CategoriesComponent implements OnInit {

  constructor(private catService:CatalogueService,private route:Router) { }
  categories:any;
  currentCategory:any;

  ngOnInit(): void {
  this.catService.getAllCatrogries()
    .subscribe(data=>{
      this.categories=data;
    },err=>{
      console.log(err);
    })
  }


  onGetProducts(cat:any) {
    this.currentCategory=cat;
    let url=cat._links.products.href
  this.route.navigateByUrl("/products/"+btoa(url));
  }
}
