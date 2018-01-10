/*  */
package fr.inria.diverse.mobileprivacyprofiler.activities;
import java.util.ArrayList;
import java.util.List;

import fr.inria.diverse.mobileprivacyprofiler.R;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.MobilePrivacyProfilerDBHelper;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.ApplicationHistory;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

//Start of user code protected additional ApplicationHistoryList_Adapter imports
// additional imports
import android.widget.Toast;
//End of user code

public class ApplicationHistoryList_Adapter extends BaseAdapter   implements Filterable{
	
	private Context context;

	/**
     * dbHelper used to autorefresh values and doing queries
     * must be set other wise most getter will return proxy that will need to be refreshed
	 */
	protected MobilePrivacyProfilerDBHelper _contextDB = null;

	private static final String LOG_TAG = ApplicationHistoryList_Adapter.class.getCanonicalName();

    private List<ApplicationHistory> applicationHistoryList;
    public List<ApplicationHistory> filteredApplicationHistoryList;
	private final Object mLock = new Object();
	private SimpleFilter mFilter;
	SharedPreferences prefs;
	//Start of user code protected additional ApplicationHistoryList_Adapter attributes
	// additional attributes
	//End of user code

	public ApplicationHistoryList_Adapter(Context context, MobilePrivacyProfilerDBHelper contextDB) {
		super();
		this.context = context;
		this._contextDB = contextDB;
		prefs = PreferenceManager.getDefaultSharedPreferences(context);
        // Start of user code protected ApplicationHistoryList_Adapter constructor
		// End of user code
		updateList();
	}
	
	protected void updateList(){
		// Start of user code protected ApplicationHistoryList_Adapter updateList
		// TODO find a way to query in a lazier way
		try{
			this.applicationHistoryList = _contextDB.applicationHistoryDao.queryForAll();
			this.filteredApplicationHistoryList = this.applicationHistoryList;
		} catch (java.sql.SQLException e) {
			Log.e(LOG_TAG, e.getMessage(), e);
		}
		// End of user code
	}

	@Override
	public int getCount() {
		if(filteredApplicationHistoryList.size() == 0){
			return 1;	// will create a dummy entry to invite changing the filters
        }
		return filteredApplicationHistoryList.size();
	}

	@Override
	public Object getItem(int position) {
		return filteredApplicationHistoryList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup viewGroup) {
		// Start of user code protected additional ApplicationHistoryList_Adapter getView_assign code
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.applicationhistorylist_listviewrow, null);
        }
		if(filteredApplicationHistoryList.size() == 0){
        	return getNoResultSubstitute(convertView);
        }
		final ApplicationHistory entry = filteredApplicationHistoryList.get(position);
		if(_contextDB != null) entry.setContextDB(_contextDB); 		
       
		// set data in the row 
		TextView tvLabel = (TextView) convertView.findViewById(R.id.applicationhistorylist_listviewrow_label);
        StringBuilder labelSB = new StringBuilder();
		labelSB.append(entry.getAppName());
		labelSB.append(" ");
        tvLabel.setText(labelSB.toString());

		// End of user code

        // assign the entry to the row in order to ease GUI interactions
        LinearLayout llRow = (LinearLayout)convertView.findViewById(R.id.applicationhistorylist_listviewrow);
        llRow.setTag(entry);
        
		// Start of user code protected additional ApplicationHistoryList_Adapter getView code
		//	additional code
		// End of user code

        return convertView;

	}

	protected View getNoResultSubstitute(View convertView){
		TextView tvLabel = (TextView) convertView.findViewById(R.id.applicationhistorylist_listviewrow_label);
		tvLabel.setText(R.string.applicationhistorylist_classlistview_no_result);
		// Start of user code protected additional ApplicationHistoryList_Adapter getNoResultSubstitute code
	/*	try{
			StringBuilder sbRechercheCourante = new StringBuilder();
	        int filtreCourantId = prefs.getInt(context.getString(R.string.pref_key_filtre_groupe), 1);	        
			if(filtreCourantId==1){
				sbRechercheCourante.append(context.getString(R.string.accueil_recherche_precedente_filtreEspece_sans));
	        }
			else {
				Groupe groupeFiltreCourant = _contextDB.groupeDao.queryForId(filtreCourantId);
				sbRechercheCourante.append(context.getString(R.string.listeficheavecfiltre_popup_filtreEspece_avec)+" "+groupeFiltreCourant.getNomGroupe().trim());
			}
			sbRechercheCourante.append(" ; ");
			int currentFilterId = prefs.getInt(context.getString(R.string.pref_key_filtre_zonegeo), -1);
	        if(currentFilterId == -1 || currentFilterId == 0){ // test sur 0, juste pour assurer la migration depuis alpha3 , a supprimer plus tard
	        	sbRechercheCourante.append(context.getString(R.string.accueil_recherche_precedente_filtreGeographique_sans));
	        }
	        else{
	        	ZoneGeographique currentZoneFilter= _contextDB.zoneGeographiqueDao.queryForId(currentFilterId);
	        	sbRechercheCourante.append(context.getString(R.string.listeficheavecfiltre_popup_filtreGeographique_avec)+" "+currentZoneFilter.getNom().trim());
	        }
	        // TODO ajouter le filtre textuel courant qui lui aussi peut impliquer de ne retourner aucun résultats
	        TextView tvDetails = (TextView) convertView.findViewById(R.id.listeficheavecfiltre_listviewrow_details);
			tvDetails.setText( sbRechercheCourante.toString() );
		} catch (SQLException e) {
			Log.e(LOG_TAG, e.getMessage(), e);
		}
    	convertView.findViewById(R.id.applicationhistorylist_listviewrow__btnEtatFiche).setVisibility(View.GONE);
    	*/
		// End of user code
		ImageView ivIcon = (ImageView) convertView.findViewById(R.id.applicationhistorylist_listviewrow_icon);
    	ivIcon.setImageResource(R.drawable.app_ic_launcher);
		return convertView;
	}
	
	//Start of user code protected additional ApplicationHistoryList_Adapter methods
	// additional methods
	//End of user code
	protected boolean sortAfterFilter() {
		return false;
	}
	
	public int filter(int position, ApplicationHistory entry, String pattern){
		// Start of user code protected additional ApplicationHistoryList_Adapter filter code
		StringBuilder labelSB = new StringBuilder();
		labelSB.append(entry.getAppName());
		labelSB.append(" ");
		if(labelSB.toString().toLowerCase().contains(pattern)) return 1;
		else return -1;
		// End of user code
	}
	
	@Override
	public Filter getFilter() {
		if (mFilter == null) {
			mFilter = new SimpleFilter();
		}
		return mFilter;
	}
	
	private class SimpleFilter extends Filter {

		@Override
		protected FilterResults performFiltering(CharSequence prefix) {
			FilterResults results = new FilterResults();

			/*if (ficheList == null) {
				synchronized (mLock) {
					ficheList = new ArrayList<Fiches>(mObjects);
				}
			}*/

			if (prefix == null || prefix.length() == 0) {
				synchronized (mLock) {
					ArrayList<ApplicationHistory> list = new ArrayList<ApplicationHistory>(applicationHistoryList);
					results.values = list;
					results.count = list.size();
				}
			} else {
		// Start of user code protected ApplicationHistoryList_Adapter filter prefix customisation
				String prefixString = prefix.toString().toLowerCase();
		// End of user code
				boolean sort = sortAfterFilter();
				final List<ApplicationHistory> values = applicationHistoryList;
				final int count = values.size();
		
				final ArrayList<ApplicationHistory> newValues = new ArrayList<ApplicationHistory>(count);
				final int[] orders = sort ? new int[count] : null;

				for (int i = 0; i < count; i++) {
					final ApplicationHistory value = values.get(i);
					int order = ApplicationHistoryList_Adapter.this.filter(i, value, prefixString);
					if (order >= 0) {
						if (sort)
							orders[newValues.size()] = order;
						newValues.add(value);
					}
				}
				/* TODO implement a sort
				if (sort) {
					Comparator<ApplicationHistory> c = new Comparator<ApplicationHistory>() {
						public int compare(ApplicationHistory object1, ApplicationHistory object2) {
							// Start of user code protected additional ApplicationHistoryList_Adapter compare code
							int i1 = newValues.indexOf(object1);
							int i2 = newValues.indexOf(object2);
							return orders[i1] - orders[i2];
							// End of user code
						}
					};
					Collections.sort(newValues, c);
				}
				*/
				results.values = newValues;
				results.count = newValues.size();
			}

			return results;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {
			
			if (results.count > 0) {
				filteredApplicationHistoryList = (List<ApplicationHistory>) results.values;
				notifyDataSetChanged();
			} else {
				filteredApplicationHistoryList = new ArrayList<ApplicationHistory>();
				notifyDataSetInvalidated();
			}
		}
	}
}
