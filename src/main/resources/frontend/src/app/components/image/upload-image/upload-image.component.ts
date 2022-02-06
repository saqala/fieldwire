import { Component, OnInit } from '@angular/core';
import { HttpEventType, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ImageService } from 'src/app/services/image-service/image.service';

@Component({
    selector: 'app-upload-image',
    templateUrl: './upload-image.component.html',
    styleUrls: ['./upload-image.component.scss']
})
export class UploadImagesComponent {
    selectedFiles?: FileList;
    selectedFileNames: any = ['insert image'];

    progressInfos: any[] = [];
    message: string[] = [];

    previews: string[] = [];

    constructor(private imageService: ImageService) { }


    selectFiles(event: any): void {
        this.message = [];
        this.progressInfos = [];
        this.selectedFileNames = [];
        this.selectedFiles = event.target.files;

        this.previews = [];
        if (this.selectedFiles && this.selectedFiles[0]) {
            const numberOfFiles = this.selectedFiles.length;
            for (let i = 0; i < numberOfFiles; i++) {
                const reader = new FileReader();

                reader.onload = (e: any) => {
                    console.log(e.target.result);
                    this.previews.push(e.target.result);
                };

                reader.readAsDataURL(this.selectedFiles[i]);
                this.selectedFileNames.push(this.selectedFiles[i].name);
            }
        }
    }

    upload(idx: number, file: File): void {
        this.progressInfos[idx] = { value: 0, fileName: file.name };

        if (file) {
            let name = this.selectedFileNames instanceof Array  ? this.selectedFileNames[idx] : this.selectedFileNames.split(',')[idx] ?? file.name;
            this.imageService.upload(file, name).subscribe(
                (event: any) => {
                    if (event.type === HttpEventType.UploadProgress) {
                        this.progressInfos[idx].value = Math.round(
                            (100 * event.loaded) / event.total
                        );
                    } else if (event instanceof HttpResponse) {
                        const msg = 'Uploaded the file successfully: ' + name;
                        this.message.push(msg);
                    }
                },
                (err: any) => {
                    this.progressInfos[idx].value = 0;
                    const msg = 'Could not upload the file: ' + name;
                    this.message.push(msg);
                }
            );
        }
    }

    uploadFiles(): void {
        this.message = [];

        if (this.selectedFiles) {
            for (let i = 0; i < this.selectedFiles.length; i++) {
                this.upload(i, this.selectedFiles[i]);
            }
        }
    }

}
