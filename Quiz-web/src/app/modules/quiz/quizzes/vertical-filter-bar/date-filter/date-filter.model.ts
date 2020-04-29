import {FBFilter} from "../fb-filter.interface";

export class DateFilter implements FBFilter {

  public filterValue: string[] = []

  currMin: Date;
  currMax: Date;

  constructor(
    public identifier: string,
    public title: string,
    public min: Date,
    public max: Date,
  ) {
    this.filterValue.push(min.toDateString())
    this.filterValue.push(max.toDateString())
    this.currMin = min;
    this.currMax = max;
  }

  public getClassName(): string {
    return 'DateFilter'
  }

  public resetFilterValue(): void {
    this.filterValue = []
    this.filterValue.push(this.min.toDateString())
    this.filterValue.push(this.max.toDateString())
  }
}
