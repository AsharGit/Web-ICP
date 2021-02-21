import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  // define list of items
  items= [];

  // Write code to push new item
  submitNewItem(value) {
    if(value!==""){
      this.items.push(value)
    }
    else{
      alert('Please enter a task')
    }
  }

  // Write code to complete item
  completeItem() {


  }

  // Write code to delete item
  deleteItem(value) {
    for(let i=0 ;i<= this.items.length ;i++){
      if(value == this.items[i]){
        this.items.splice(i,1)
      }
    }

  }

}
