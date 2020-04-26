import { Component, OnInit, Input, EventEmitter, Output, ViewEncapsulation } from '@angular/core';
import { DateFilter } from './date-filter.model';
import { FBFilterComponent } from '../fb-filter-component.interface';

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
    this.model.filterValue[0]=this.model.currMin.toDateString();
    this.model.filterValue[1]=this.model.currMax.toDateString();
    if(this.model.filterValue[0] === this.model.min.toDateString() &&
      this.model.filterValue[1] === this.model.max.toDateString())
      this.change.emit(undefined)
    else{
      this.change.emit(this.model.filterValue)
    }
  }
}
