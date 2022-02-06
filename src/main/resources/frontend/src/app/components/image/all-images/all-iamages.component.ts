import { Inject } from '@angular/core';
import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { ImagePage } from 'src/app/model/image-page.inteface';
import { CustomPageEvent } from 'src/app/model/page-event-custom.interface';
import { ImageService } from 'src/app/services/image-service/image.service';
import { BASE_URL } from 'src/environments/environment';
import { ImagePopup } from '../popup/image-popup.component';

@Component({
  selector: 'app-all-images',
  templateUrl: './all-images.component.html',
  styleUrls: ['./all-images.component.scss']
})
export class AllImagesComponent {

  @Input() imagePage: ImagePage;
  @Output() refreshEvent: EventEmitter<CustomPageEvent> = new EventEmitter<CustomPageEvent>();

  pageEvent: CustomPageEvent;

  baseUrl = BASE_URL;
  type : boolean = false;
  searchWord: string;
  sortValues = ['asc', 'desc', ''];
  sortIndex = 0;
  lastEvent: CustomPageEvent = {};
  toBeEdited: number;
  newImageName: string;

  constructor(private imageService: ImageService, private router: Router, public  dialog: MatDialog) { }


  onPaginateChange(event: CustomPageEvent) {
    event.pageIndex = event.pageIndex;
    event.search = this.searchWord;
    event.sort = this.sortValues[this.sortIndex];
    this.lastEvent = event;
    this.refreshEvent.emit(event);
  }

  search() {
    this.lastEvent.search = this.searchWord;
    this.refreshEvent.emit(this.lastEvent);
  }

  sort() {
    this.lastEvent.sort = this.nextSortValue();
    this.refreshEvent.emit(this.lastEvent);
  }

  nextSortValue() {
    this.sortIndex = this.sortIndex + 1;
    this.sortIndex = this.sortIndex % this.sortValues.length;
    return this.sortValues[this.sortIndex];
  }

  delete(id: any) {
    this.imageService.delete(id).subscribe(() => this.refreshEvent.emit(this.lastEvent));
  }

  edit(id: number) {
    this.toBeEdited = id;
  }

  validate(id: number) {
    this.imageService.patch(this.toBeEdited, this.newImageName).subscribe(() => this.refreshEvent.emit(this.lastEvent));
    this.toBeEdited = null;
    this.newImageName = "";
  }

  cancel() {
    this.toBeEdited = null;
    this.newImageName = "";
  }


  openDialog(): void {
    const dialogRef = this.dialog.open(ImagePopup, { width: '750px' })

    dialogRef.afterClosed().subscribe(result => {
      this.refreshEvent.emit(this.lastEvent);
    });
  }

  switchThumb () {
    this.type = !this.type;
    this.lastEvent.type = this.type ? 'thumb' : null;  
    this.refreshEvent.emit(this.lastEvent);

  }

}
