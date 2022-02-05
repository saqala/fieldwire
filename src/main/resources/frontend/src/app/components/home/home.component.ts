import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { ImagePage } from 'src/app/model/image-page.inteface';
import { CustomPageEvent } from 'src/app/model/page-event-custom.interface';
import { ImageService } from 'src/app/services/image-service/image.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  imagePage: Observable<ImagePage>;

  defaultPage = 0;
  defaultElements = 12;
  defaultSort = '';
  defaultSearch = '';
  defaultType = '';

  constructor(private imageService: ImageService) { }

  ngOnInit() {

    this.imagePage = this.imageService.indexAll(this.defaultPage, this.defaultElements, this.defaultSearch, this.defaultSort, this.defaultType);
  }

  onPaginateChange(event: CustomPageEvent) {
    this.imagePage = this.imageService.indexAll(event.pageIndex ?? this.defaultPage , event.pageSize ?? this.defaultElements, 
      event.search ?? this.defaultSearch, event.sort ?? this.defaultSort, event.type ?? this.defaultType);
  }

}
