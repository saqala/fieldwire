import { HttpClient, HttpEvent, HttpParams, HttpRequest, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ImageFile } from 'src/app/model/image-file.interface';
import { ImagePage } from 'src/app/model/image-page.inteface';
import { IMAGES_URL } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ImageService {

  constructor(private http: HttpClient) { }

  indexAll(page: number, limit: number, search: string, sort: string, type: string): Observable<ImagePage> {
    let params = new HttpParams();

    params = params.append('page', String(page));
    params = params.append('elements', String(limit));
    params = params.append('search', search);
    params = params.append('sort', sort);
    params = params.append('type', type);
    return this.http.get<ImagePage>(IMAGES_URL, {params});
  }

  delete(id: number): Observable<any> {
    return this.http.delete<any>(IMAGES_URL + id);
  }

  patch(id: number, name: string): Observable<any> {
    const httpHeaders = new HttpHeaders({ 'Content-Type' : 'application/json' });
    const body = { 'id': id, 'name': name };
    return this.http.patch<any>(IMAGES_URL, body, {headers: httpHeaders});
  }

  upload(file: File, name : string): Observable<HttpEvent<any>> {
    const formData: FormData = new FormData();

    formData.append('imageFile', file);
    formData.append('name', name);

    const req = new HttpRequest('POST', IMAGES_URL, formData, {
      reportProgress: true,
      responseType: 'json'
    });

    return this.http.request(req);
  }

}
