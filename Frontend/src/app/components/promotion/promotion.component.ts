import { Component, OnDestroy, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Subscription } from 'rxjs';
import { IPromotion } from 'src/app/models/ipromotion';
import { PromotionService } from 'src/app/services/promotion.service';
import { DialogBoxComponent } from '../dialog-box/dialog-box.component';
import { UserComponent } from '../user/user.component';

@Component({
  selector: 'app-promotion',
  templateUrl: './promotion.component.html',
  styleUrls: ['./promotion.component.scss']
})
export class PromotionComponent implements OnInit, OnDestroy {

  subscription: Subscription | undefined;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild('users') usersDialog: TemplateRef<any>;
  dataSource: MatTableDataSource<IPromotion>;
  dataLength: number;
  promotionId: number;

  displayedColumns = [
    "S.No",
    "Promotion Name",
    "Gender",
    "Minimum Age",
    "Maximum Age",
    "Status",
    "Start Time",
    "End Time",
    "Action",
  ];

  constructor(private _service: PromotionService, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.subscription = this._service
      .getPromotions()
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

  deletePromotion(id: number) {
    const confirmDialog = this.dialog.open(DialogBoxComponent, {
      data: {
        title: 'Confirm Delete Promotion',
        message: 'Are you sure, you want to delete promotion?'
      }
    });
    confirmDialog.afterClosed().subscribe(result => {
      if (result === true) {
        this._service.deletePromotion(id).subscribe((res: any) => {
          location.reload();
        });
      }
    });
  }

  showUsers(id: number) {
    this.dialog.open(this.usersDialog);
    this.promotionId = id;
  }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }
}
