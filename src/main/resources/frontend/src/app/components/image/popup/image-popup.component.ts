import { Component, Inject } from "@angular/core";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material/dialog";

@Component({
    selector: 'app-image-popup',
    templateUrl: 'image-popup.component.html',
  })
  export class ImagePopup {
  
    constructor(
      public dialogRef: MatDialogRef<ImagePopup>,
      @Inject(MAT_DIALOG_DATA) public data: any
      ) {}
  
    onNoClick(): void {
      this.dialogRef.close();
    }
  
  }