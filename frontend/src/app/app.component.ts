import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'frontend';

  candidate = 'Pedro Luiz da Sila Pereira';

  summaries: any;

  constructor(private http: HttpClient) { }


  getAll() {
  this.http.get('http://localhost:8080/api/summary').subscribe(data => {
    console.log("backend response") ;
    this.summaries = data;
    
  });
  


}

}
