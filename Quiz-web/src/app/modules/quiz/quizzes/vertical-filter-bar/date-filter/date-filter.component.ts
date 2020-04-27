import { Component, OnInit, Input, EventEmitter, Output, ViewEncapsulation } from '@angular/core';
import { DateFilter } from './date-filter.model';
import { FBFilterComponent } from '../fb-filter-component.interface';
import {DatePipe} from "@angular/common";
@Component({
  selector: 'app-range-filter',
  templateUrl: './date-filter.component.html',
  styleUrls: ['./date-filter.component.css'],
})
export class DateFilterComponent implements OnInit, FBFilterComponent {

  @Input()
  public model: DateFilter

  @Output()
  change: EventEmitter<string[]> = new EventEmitter();


  constructor() { }

  ngOnInit(): void {
  }

  rangeChanged(_event: any): void {
    //let val=(<HTMLInputElement>document.getElementById('min')).value
    this.model.filterValue[0]=this.model.currMin.toISOString().slice(0,10);
    this.model.filterValue[1]=this.model.currMax.toISOString().slice(0,10);
    if(this.model.filterValue[0] === this.model.min.toISOString().slice(0,10) &&
      this.model.filterValue[1] === this.model.max.toISOString().slice(0,10))
      this.change.emit(undefined)
    else{
      this.change.emit(this.model.filterValue)
    }
  }
}
