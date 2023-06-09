import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TagDetailComponent } from './tag-detail.component';

describe('Tag Management Detail Component', () => {
  let comp: TagDetailComponent;
  let fixture: ComponentFixture<TagDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TagDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ tag: { id: '9fec3727-3421-4967-b213-ba36557ca194' } }) },
        },
      ],
    })
      .overrideTemplate(TagDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TagDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load tag on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.tag).toEqual(expect.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
    });
  });
});
