import { Component, OnInit } from '@angular/core';
import {CatalogueService} from '../catalogue.service';

@Component({
  selector: 'app-admin-categories',
  templateUrl: './admin-categories.component.html',
  styleUrls: ['./admin-categories.component.css']
})
export class AdminCategoriesComponent implements OnInit {
  constructor(private catalogueService: CatalogueService) {
  }

  categories;
  modes: string = 'list';
  currentCategory;

  ngOnInit(): void {
    this.onGetAllCategories();
  }

  onGetAllCategories() {
    this.catalogueService.getAllCatrogries()
      .subscribe(data => {
        this.categories = data;
      }, err => {
        console.log(err);
      })
  }

  onDeleteCategory(c) {
    let conf = confirm("Ete Vous Sure de supprimer ?");
    if (!conf) return;
    this.catalogueService.DeleteRessources(c._links.self.href).subscribe(ca => {
      this.onGetAllCategories();
    }, err => {
      console.log(err);
    })

  }

  onAddCategory() {
    this.modes = "new-cat";
  }

  onSaveCategory(data) {
    let url = this.catalogueService.host + "/categories"
    this.catalogueService.postRessources(url, data)
      .subscribe(ca => {
        this.modes = 'list';
        this.onGetAllCategories();
      }, err => {
        console.log(err);
      })

  }

  onEditCategory(cc) {
    this.catalogueService.getRessources(cc._links.self.href)
      .subscribe(data => {
        this.currentCategory = data;
        this.modes = 'edit-cat';

      }, err => {
        console.log(err);
      })
  }

  onUpdateCategory(data) {
    this.catalogueService.putRessources(this.currentCategory._links.self.href, data)
      .subscribe(ca => {
        this.modes = 'list';
        this.onGetAllCategories();
      }, err => {
        console.log(err);
      })

  }
}
