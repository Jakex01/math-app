import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Answer} from "../../models/answer.model";
import {CommentRequest} from "../../models/comment-request";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  private baseUrl = 'http://localhost:8080/comment';


  constructor(private http: HttpClient) { }


  submitComment(commentRequest: CommentRequest): Observable<any> {

    console.log(commentRequest);

    return this.http.post(`${this.baseUrl}/post`, commentRequest);
  }

}
