import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { ImagePopup } from './components/image/popup/image-popup.component';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'frontend';
  mySubscription : any;

  constructor(private router: Router, public  dialog: MatDialog) {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    this.mySubscription = this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
         this.router.navigated = false;
      }
    }); 
  }

  ngOnDestroy(){
    console.log('app ngOnDestroy')
    if (this.mySubscription) {
     this.mySubscription.unsubscribe();
    }
  }

}
