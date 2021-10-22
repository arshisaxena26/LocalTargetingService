import { Component, Input, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Subscription } from 'rxjs';
import { IUser } from 'src/app/models/iuser';
import { PromotionService } from 'src/app/services/promotion.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit, OnDestroy {

  subscription: Subscription | undefined;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  dataSource: MatTableDataSource<IUser>;
  dataLength: number;
  @Input() promotionId: any;

  displayedColumns = [
    "S.No",
    "Name",
    "Email",
    "DOB",
    "Gender"
  ];

  constructor(private _service: PromotionService) { }

  ngOnInit(): void {
    this.subscription = this._service
      .getUsers(this.promotionId)
      .subscribe((res: any) => {
        this.dataLength = res.length;
        this.dataSource = new MatTableDataSource(res);
        setTimeout(() => {
          this.dataSource.paginator = this.paginator;
        });
      });
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  ngOnDestroy(): void {
    console.log("Running");
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

}
