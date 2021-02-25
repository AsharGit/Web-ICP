import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-search-recipe',
  templateUrl: './search-recipe.component.html',
  styleUrls: ['./search-recipe.component.css']
})
export class SearchRecipeComponent implements OnInit {
  @ViewChild('recipe') recipes: ElementRef;
  @ViewChild('place') places: ElementRef;
  recipeValue: any;
  placeValue: any;
  venueList = [];
  recipeList = [];
  recipeURL = 'https://api.edamam.com/search?q=';
  recipeID = '&app_id=a3db3af6';
  recipeKEY = '&app_key=a89aaf598f12da8a34048bf972cdfb8f';
  locationURL = 'https://api.foursquare.com/v2/venues/search?near=';
  locationID = '&client_id=N1D1RUCTGHKFJLOSGIFL4GNE3DUG3TSABCHEVOHLWBEHJCQJ';
  locationSecret = '&client_secret=XXJ5Q0ONFMGSB24XNQGSAAIAY0HX5FGYPNRTUQBPPFTUO0I0';
  locationVersion = '&v=20210101';


  currentLat: any;
  currentLong: any;
  geolocationPosition: any;

  constructor(private _http: HttpClient) {
  }

  ngOnInit() {

    window.navigator.geolocation.getCurrentPosition(
      position => {
        this.geolocationPosition = position;
        this.currentLat = position.coords.latitude;
        this.currentLong = position.coords.longitude;
      });
  }

  getVenues() {

    this.recipeValue = this.recipes.nativeElement.value;
    this.placeValue = this.places.nativeElement.value;

    if (this.recipeValue !== null) {
      // Using get method retrieve info from recipe API and store in list
      this._http.get(this.recipeURL + this.recipeValue + this.recipeID + this.recipeKEY).subscribe((result: any) => {
        this.recipeList = Object.keys(result.hits).map(function (k) {
          const i = result.hits[k].recipe;
          return {icon: i.image, name: i.label, url: i.url};
        });
      });
    }

    if (this.placeValue != null && this.placeValue !== '' && this.recipeValue != null && this.recipeValue !== '') {
      // Using get method retrieve info from location API and store in list
      this._http.get(this.locationURL + this.placeValue + this.locationID +
        this.locationSecret + this.locationVersion).subscribe((result: any) => {
        this.venueList = Object.keys(result.response.venues).map(function (k) {
          const i = result.response.venues[k];
          return {name: i.name, currentLat: i.location.labeledLatLngs.lat, currentLong: i.location.labeledLatLngs.lng};
        });
      });

    }
  }
}

